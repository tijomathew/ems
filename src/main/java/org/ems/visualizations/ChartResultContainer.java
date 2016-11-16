package org.rms.visualizations;


/**
 * Created by tijo on 2/12/15.
 */
public final class ChartResultContainer {

    private String version;
    private String reqId;
    private String status;
    private ChartResultRow table;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getReqId() {
        return reqId;
    }

    public void setReqId(String reqId) {
        this.reqId = reqId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ChartResultRow getTable() {
        return table;
    }

    public void setTable(ChartResultRow table) {
        this.table = table;
    }
}
