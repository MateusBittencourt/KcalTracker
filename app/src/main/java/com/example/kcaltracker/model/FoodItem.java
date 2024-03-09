package com.example.kcaltracker.model;

import static java.lang.Float.parseFloat;

import org.json.JSONException;
import org.json.JSONObject;

public class FoodItem {
    public String tipo;
    public String descricao;
    public float umidade;
    public float energia_kcal;
    public float energia_kJ;
    public float proteina;
    public float lipideos;
    public float saturados;
    public float monoinsaturados;
    public float poliinsaturados;
    public float colesterol;
    public float carboidrato;
    public float fibra_alimentar;
    public float cinzas;
    public float calcio;
    public float magnesio;
    public float manganes;
    public float fosforo;
    public float ferro;
    public float sodio;
    public float potassio;
    public float cobre;
    public float zinco;
    public float retinol;
    public float re;
    public float rae;
    public float tiamina;
    public float riboflavina;
    public float piridoxina;
    public float niacina;
    public float vitamina_c;

    public FoodItem(JSONObject obj) throws JSONException {
        this.tipo = obj.getString("tipo");
        this.descricao = obj.getString("descricao");
        this.umidade = parseFloat(obj.getString("umidade"));
        this.energia_kcal = parseFloat(obj.getString("energia_kcal"));
        this.energia_kJ = parseFloat(obj.getString("energia_kJ"));
        this.proteina = parseFloat(obj.getString("proteina"));
        this.lipideos = parseFloat(obj.getString("lipideos"));
        this.saturados = parseFloat(obj.getString("saturados"));
        this.monoinsaturados = parseFloat(obj.getString("monoinsaturados"));
        this.poliinsaturados = parseFloat(obj.getString("poliinsaturados"));
        this.colesterol = parseFloat(obj.getString("colesterol"));
        this.carboidrato = parseFloat(obj.getString("carboidrato"));
        this.fibra_alimentar = parseFloat(obj.getString("fibra_alimentar"));
        this.cinzas = parseFloat(obj.getString("cinzas"));
        this.calcio = parseFloat(obj.getString("calcio"));
        this.magnesio = parseFloat(obj.getString("magnesio"));
        this.manganes = parseFloat(obj.getString("manganes"));
        this.fosforo = parseFloat(obj.getString("fosforo"));
        this.ferro = parseFloat(obj.getString("ferro"));
        this.sodio = parseFloat(obj.getString("sodio"));
        this.potassio = parseFloat(obj.getString("potassio"));
        this.cobre = parseFloat(obj.getString("cobre"));
        this.zinco = parseFloat(obj.getString("zinco"));
        this.retinol = parseFloat(obj.getString("retinol"));
        this.re = parseFloat(obj.getString("re"));
        this.rae = parseFloat(obj.getString("rae"));
        this.tiamina = parseFloat(obj.getString("tiamina"));
        this.riboflavina = parseFloat(obj.getString("riboflavina"));
        this.piridoxina = parseFloat(obj.getString("piridoxina"));
        this.niacina = parseFloat(obj.getString("niacina"));
        this.vitamina_c = parseFloat(obj.getString("vitamina_c"));
    }

}
