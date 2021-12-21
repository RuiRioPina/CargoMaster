package lapr.project.model;

public class AuditTrail {

    private final String author;
    private final String date;
    private final String nrContainer;
    private final String action;
    private final String idCargo;
    private final String idManifest;

    public AuditTrail(String author, String date, String nrContainer, String action, String idCargo, String idManifest) {
        this.author = author;
        this.date = date;
        this.nrContainer = nrContainer;
        this.action = action;
        this.idCargo = idCargo;
        this.idManifest = idManifest;
    }

    @Override
    public String toString() {
        return "AuditTrail - " +
                "Author = '" + author + '\'' +
                " Date = '" + date + '\'' +
                " nrContainer = '" + nrContainer + '\'' +
                " Action = '" + action + '\'' +
                " idCargo = '" + idCargo + '\'' +
                " idManifest = '" + idManifest
                ;
    }
}
