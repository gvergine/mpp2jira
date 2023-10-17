module gvergine.mpp2jira {
    requires transitive javafx.controls;
    requires transitive javafx.fxml;
	requires javafx.graphics;
	requires javafx.base;
	requires mpxj;
	requires java.base;
	requires jira.rest.java.client.api;
	requires jira.rest.java.client.core;
	requires transitive atlassian.util.concurrent;
	requires atlassian.httpclient.api;
	requires org.joda.time;

    exports gvergine.mpp2jira;
    
    opens gvergine.mpp2jira to javafx.fxml, javafx.base;
    opens gvergine.mpp2jira.exceptions to javafx.fxml, javafx.base;
    opens gvergine.mpp2jira.controllers to javafx.fxml, javafx.base;
    opens gvergine.mpp2jira.model to javafx.base;
}
