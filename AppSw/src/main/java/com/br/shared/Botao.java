/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.br.shared;

import jiconfont.icons.font_awesome.FontAwesome;
import jiconfont.swing.IconFontSwing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

/**
 *
 * @author Deivy
 */
public class Botao extends JButton {

    public Botao() {
    }

    public Botao(FontAwesome fontAwesome) {
        super(IconFontSwing.buildIcon(fontAwesome, 15, Color.lightGray));
    }
    
    public Botao(String nome, Integer largura){
    	super(nome);
  		setMinimumSize(new Dimension((largura==null ? 100 : largura.intValue()), 22));
    	setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    public Botao(String nome, Icon icon){
        super(nome, icon);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
    }
    
    public Botao(String nome, Icon icon, Integer largura){
    	super(nome, icon);
    	setMinimumSize(new Dimension((largura==null ? 100 : largura.intValue()), 22));
    	setCursor(new Cursor(Cursor.HAND_CURSOR));
    }
    public Botao(String nome, Icon icon, Integer largura, int mnemonic){
    	super(nome, icon);
    	setMinimumSize(new Dimension((largura==null ? 100 : largura.intValue()), 22));
    	setCursor(new Cursor(Cursor.HAND_CURSOR));
    	setMnemonic(mnemonic);
    }
    
    public Botao botaoPesquisa() {
    	return new Botao("Pesquisar", IconFontSwing.buildIcon(FontAwesome.SEARCH, 15, Color.lightGray), null, KeyEvent.VK_P);
    }

    public Botao botaoNovo() {
    	return new Botao("Novo", IconFontSwing.buildIcon(FontAwesome.PLUS_SQUARE_O, 15, Color.lightGray), null, KeyEvent.VK_N);
    }

    public Botao botaoSalvar() {
    	return new Botao("Salvar", IconFontSwing.buildIcon(FontAwesome.CHECK, 15, Color.lightGray), null, KeyEvent.VK_S);
    }

    public Botao botaoFechar() {
    	return new Botao("Fechar", IconFontSwing.buildIcon(FontAwesome.TIMES, 15, Color.RED), null, KeyEvent.VK_F);
    }

    public Botao botaoLimpar() {
        return new Botao("Limpar", IconFontSwing.buildIcon(FontAwesome.ERASER, 15, Color.lightGray), null, KeyEvent.VK_F);
    }


}
