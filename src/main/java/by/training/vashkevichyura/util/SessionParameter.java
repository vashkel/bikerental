package by.training.vashkevichyura.util;

public enum SessionParameter {

        LANGUAGE("lang"),
        LOCAL("local"),
        MESSAGE("message"),
        USER("user"),
        PAGE_INFO("pageInfo");

        private String parameter;

        SessionParameter(String paramName) {
                this.parameter = paramName;
        }

        /**
         * @return String representation of the parameter.
         */
        public String parameter() {
                return parameter;
        }
}
