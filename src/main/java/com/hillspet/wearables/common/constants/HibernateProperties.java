package com.hillspet.wearables.common.constants;

public enum HibernateProperties {

    SHOW_SQL("hibernate.show_sql"),
    BATCH_SIZE("hibernate.jdbc.batch_size"),
	GENERATE_STATISTICS("hibernate.generate_statistics"),
	LOG_SQL("org.hibernate.SQL");

    private String propertyName;

    private HibernateProperties(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getPropertyName() {
        return propertyName;
    }

}
