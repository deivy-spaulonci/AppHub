package com.br.fornecedor;

import com.br.dao.CidadeDAO;
import com.br.dao.FornecedorDAO;
import com.br.dto.response.FornecedorCloudResponseDTO;
import com.br.entity.Cidade;
import com.br.entity.Estado;
import com.br.entity.Fornecedor;
import com.br.shared.Botao;
import com.br.shared.CampoMascaraPadrao;
import com.br.shared.InputTexto;
import com.br.shared.LoadingDialog;
import com.br.util.Util;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jiconfont.icons.font_awesome.FontAwesome;
import lombok.extern.log4j.Log4j2;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

@Log4j2
public class FornecedorForm extends JPanel {

    private CidadeDAO cidadeDAO = new CidadeDAO();
    private FornecedorDAO fornecedorDAO = new FornecedorDAO();
    private CampoMascaraPadrao inputCnpj = new CampoMascaraPadrao("##.###.###/####-##", 15);
    private InputTexto inputCpf = new InputTexto(15);
    private InputTexto inputNome = new InputTexto(30);
    private InputTexto inputRazao = new InputTexto(30);
    private Botao btCloud = new Botao(FontAwesome.CLOUD);
    private JComboBox<Estado> cbxEstado;
    private JComboBox<Cidade> cbxCidade;
    private DefaultComboBoxModel modelCidade = new DefaultComboBoxModel();
    private Botao salvar;
    private String RECEITA_AWS = "https://publica.cnpj.ws/cnpj/";

    public FornecedorForm() {
        init();
    }

    public void init(){
        JRadioButton cnp = new JRadioButton("Juridica", true);
        JRadioButton cpf = new JRadioButton("Fisica", false);

//        cnp.setIcon(IconFontSwing.buildIcon(FontAwesome.BUILDING, 15, Color.lightGray));
//        cpf.setIcon(IconFontSwing.buildIcon(FontAwesome.USER, 15, Color.lightGray));

        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(cnp);
        buttonGroup.add(cpf);

        inputCpf.setEditable(false);

        cnp.addActionListener(al -> {
            inputCpf.setEditable(false);
            inputCnpj.setEditable(true);
        });
        cpf.addActionListener(al -> {
            inputCpf.setEditable(true);
            inputCnpj.setEditable(false);
        });

        btCloud.setPreferredSize(new Dimension(60, 35));
        btCloud.addActionListener(al -> findCNPJCloud());

        cbxEstado = new JComboBox<>(Estado.values());
        cbxCidade = new JComboBox<>(modelCidade);
        cbxCidade.setPreferredSize(new Dimension(400, 20));

        cbxEstado.addActionListener(e -> {
            List<Cidade> cidades = cidadeDAO.getCidade(((JComboBox) e.getSource()).getModel().getSelectedItem().toString());
            modelCidade.removeAllElements();
            cidades.forEach(c -> modelCidade.addElement(c));
        });

        salvar = new Botao().botaoSalvar();

        setLayout(new MigLayout());

        add(new JLabel("Pessoa: "));
        add(cnp, "split 2");
        add(cpf, "wrap");
        add(new JLabel("CNPJ: "));
        add(inputCnpj, "split 2");
        add(btCloud, "wrap");
        add(new JLabel("CPF: "));
        add(inputCpf,  "wrap");
        add(new JLabel("Nome: "));
        add(inputNome, "wrap");
        add(new JLabel("Razão: "));
        add(inputRazao, "wrap");
        add(new JLabel("Cidade: "));
        add(cbxEstado,"split 2");
        add(cbxCidade,"wrap");
        add(new JLabel(""));
        add(salvar);
    }

    public void findCNPJCloud(){

        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        LoadingDialog loadingDialog = new LoadingDialog(parentFrame);

        SwingWorker<Void, Void> sw = new SwingWorker<>() {
            @Override
            protected Void doInBackground() throws Exception {
                try {
                    String cnpj = inputCnpj.getText().replaceAll("[^0-9]","");
                    if(cnpj.length() == 14){
                        if(!fornecedorDAO.getFornecedorByFilter(cnpj).isEmpty()){
                            Util.alertErro(null, "Fornecedor ja cadastrado!");
                        }else{
                            Fornecedor fornecedor = getFornecedorFromWeb(cnpj);
                            if(fornecedor == null){
                                Util.alertErro(null, "Erro na busca por fornecedor em nuvem!");
                            }else{
                                inputNome.setText(fornecedor.getNome());
                                inputRazao.setText(fornecedor.getRazaoSocial());
                                cbxEstado.setSelectedItem(Estado.forValues(fornecedor.getCidade().getUf()));
                                cbxCidade.setSelectedItem(fornecedor.getCidade());
                            }
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
            @Override
            protected void done() {
                loadingDialog.closeLoading();
            }
        };
        sw.execute();
        loadingDialog.showLoading();
    }

    public Fornecedor getFornecedorFromWeb(String cnpj){
//        String json = getFornecedorApiWeb(cnpj);
//        ObjectMapper objectMapper = new ObjectMapper();
//        try {
//            FornecedorCloudResponseDTO fornecedorAWS = objectMapper.readValue(json, FornecedorCloudResponseDTO.class);
//            Cidade cidade = cidadeDAO.buscarPorCodIbge(fornecedorAWS.getIbgeCod());
//
//            Fornecedor fornecedor = Fornecedor.builder()
//                    .cnpj(fornecedorAWS.getCnpj().replaceAll("[^0-9]",""))
//                    .razaoSocial(fornecedorAWS.getNome() == null ? fornecedorAWS.getFantasia() : fornecedorAWS.getNome())
//                    .nome(fornecedorAWS.getFantasia()==null ? fornecedorAWS.getNome() : fornecedorAWS.getFantasia())
//                    .cpf("")
//                    //.ibgeCod(fornecedorAWS.getIbgeCod())
//                    .cidade(cidade)
//                    .build();
//
//            return fornecedor;
//
//        } catch (JsonProcessingException e) {
//            Util.alertErro(this, "Erro na busca por fornecedor em nuvem!");
//            log.error(e);
//            throw new RuntimeException(e);
//        }
        return null;
    }

    public String getFornecedorApiWeb(String cnpj){
        try{
            String URL_API = RECEITA_AWS+cnpj;
            HttpURLConnection con = null;
            URL url = null;
            url = new URL(URL_API);
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.connect();
            return getJson(url);
        }catch (Exception e){
            log.error(e);
        }
        return null;
    }

    public static String getJson(URL url) {
        if (url == null)
            throw new RuntimeException("URL é null");

        String html = null;
        StringBuilder sB = new StringBuilder();
        try (BufferedReader bR = new BufferedReader(new InputStreamReader(url.openStream()))) {
            while ((html = bR.readLine()) != null)
                sB.append(html);
        } catch (Exception e) {
            log.error(e);
        }

        return sB.toString();
    }
}
