package com.example.user.foodhandbook.activity.model;

/**
 * Created by User on 06/02/2018.
 */

public class TienIch {
    private int idtienich;
    private double vido;
    private double kinhdo;
    private String chude;
    private String chuthich;
    private String video;
    private int id;

    public TienIch() {
    }

    public TienIch(int idtienich, double vido, double kinhdo, String chude, String chuthich, String video, int id) {
        this.idtienich = idtienich;
        this.vido = vido;
        this.kinhdo = kinhdo;
        this.chude = chude;
        this.chuthich = chuthich;
        this.video = video;
        this.id = id;
    }

    public int getIdtienich() {
        return idtienich;
    }

    public void setIdtienich(int idtienich) {
        this.idtienich = idtienich;
    }

    public double getVido() {
        return vido;
    }

    public void setVido(double vido) {
        this.vido = vido;
    }

    public double getKinhdo() {
        return kinhdo;
    }

    public void setKinhdo(double kinhdo) {
        this.kinhdo = kinhdo;
    }

    public String getChude() {
        return chude;
    }

    public void setChude(String chude) {
        this.chude = chude;
    }

    public String getChuthich() {
        return chuthich;
    }

    public void setChuthich(String chuthich) {
        this.chuthich = chuthich;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
