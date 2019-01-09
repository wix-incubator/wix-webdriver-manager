package com.wixpress.automation.webdriver.binaries;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

public class WebDriverBinaryVersion {

    private String chromeDriverVersion = "";
    private String fireFoxDriverVersion = "";
    private String internetExplorerDriverVersion = "";

    public WebDriverBinaryVersion() {
        initializeWebDriverBinariesFromPropertiesFile();
    }

    public String chromeDriverBinaryVersion() {
        return chromeDriverVersion;
    }

    public String firefoxDriverBinaryVersion() {
        return fireFoxDriverVersion;
    }

    public String internetExplorerBinaryVersion() {
        return internetExplorerDriverVersion;
    }

    private void initializeWebDriverBinariesFromPropertiesFile() {
        final String chromeProperty = "wdm.chromedriver.version";
        final String fireFoxProperty = "wdm.geckodriver.version";
        final String internetExplorerProperty = "wdm.iexplorer.version";

        PropertiesConfiguration propertiesConfiguration;
        try {
            propertiesConfiguration = new PropertiesConfiguration("wdm.properties");
        } catch (ConfigurationException e) {
            throw new RuntimeException("No properties file succeeded to initialize!", e);
        }

        chromeDriverVersion = propertiesConfiguration.getString(chromeProperty);
        fireFoxDriverVersion = propertiesConfiguration.getString(fireFoxProperty);
        internetExplorerDriverVersion = propertiesConfiguration.getString(internetExplorerProperty);
    }
}
