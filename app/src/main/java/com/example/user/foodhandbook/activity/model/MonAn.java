package com.example.user.foodhandbook.activity.model;

import java.io.Serializable;

/**
 * Created by User on 24/01/2018.
 */

public class MonAn implements Serializable {
    private int id;
    private String tenmon;
    private String hinhmon;
    private String motamon;
    private String nguyenlieu;
    private String cachlam;
    private int idloai;

    public MonAn() {
    }

    public MonAn(int id, String tenmon, String hinhmon, String motamon, String nguyenlieu, String cachlam, int idloai) {
        this.id = id;
        this.tenmon = tenmon;
        this.hinhmon = hinhmon;
        this.motamon = motamon;
        this.nguyenlieu = nguyenlieu;
        this.cachlam = cachlam;
        this.idloai = idloai;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTenmon() {
        return tenmon;
    }

    public void setTenmon(String tenmon) {
        this.tenmon = tenmon;
    }

    public String getHinhmon() {
        return hinhmon;
    }

    public void setHinhmon(String hinhmon) {
        this.hinhmon = hinhmon;
    }

    public String getMotamon() {
        return motamon;
    }

    public void setMotamon(String motamon) {
        this.motamon = motamon;
    }

    public String getNguyenlieu() {
        return nguyenlieu;
    }

    public void setNguyenlieu(String nguyenlieu) {
        this.nguyenlieu = nguyenlieu;
    }

    public String getCachlam() {
        return cachlam;
    }

    public void setCachlam(String cachlam) {
        this.cachlam = cachlam;
    }

    public int getIdloai() {
        return idloai;
    }

    public void setIdloai(int idloai) {
        this.idloai = idloai;
    }
}
