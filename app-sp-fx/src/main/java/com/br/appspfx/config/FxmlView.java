package com.br.appspfx.config;

public enum FxmlView {

    DESPESAS {
        @Override
        public String getFxmlPath() {
            return "/view/listDespesa.fxml";
        }
    },
    MAIN {
        @Override
        public String getFxmlPath() {
            return "/view/menu.fxml";
        }
    },

    HOME {
        @Override
        public String getFxmlPath() {
            return "/view/home.fxml";
        }
    };

    public abstract String getFxmlPath();
}
