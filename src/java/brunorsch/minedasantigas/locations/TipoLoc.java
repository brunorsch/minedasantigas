package brunorsch.minedasantigas.locations;

public enum TipoLoc {
    HOME,
    LOJA,
    PWARP;

    public boolean isOwnerBased() {
        return this != LOJA;
    }

    public String tratarQuery(String queryBase) {
        if(isOwnerBased()) {
            return queryBase + " AND l.player = ?";
        } else return queryBase;
    }
}