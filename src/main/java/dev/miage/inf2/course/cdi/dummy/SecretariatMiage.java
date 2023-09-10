package dev.miage.inf2.course.cdi.dummy;

import jakarta.inject.Inject;

public class SecretariatMiage {

    Apogée apogée;

    @Inject
    MonMaster monMaster;


    public Ecandidat getEcandidat() {
        return ecandidat;
    }

    @Inject
    public void setEcandidat(Ecandidat ecandidat) {
        this.ecandidat = ecandidat;
    }

    Ecandidat ecandidat;

    @Inject
    public SecretariatMiage(Apogée apogée) {
        this.apogée = apogée;
    }
}
