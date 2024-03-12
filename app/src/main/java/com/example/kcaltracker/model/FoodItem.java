package com.example.kcaltracker.model;

import static java.lang.Float.parseFloat;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

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

    public FoodItem(ArrayList<String> obj)  {
        this.tipo = obj.get(1);
        this.descricao = obj.get(2);
        this.umidade = parseFloatSafe(obj.get(3));
        this.energia_kcal = parseFloatSafe(obj.get(4));
        this.energia_kJ = parseFloatSafe(obj.get(5));
        this.proteina = parseFloatSafe(obj.get(6));
        this.lipideos = parseFloatSafe(obj.get(7));
        this.saturados = parseFloatSafe(obj.get(8));
        this.monoinsaturados =parseFloatSafe(obj.get(9));
        this.poliinsaturados = parseFloatSafe(obj.get(10));
        this.colesterol = parseFloatSafe(obj.get(11));
        this.carboidrato = parseFloatSafe(obj.get(12));
        this.fibra_alimentar = parseFloatSafe(obj.get(13));
        this.cinzas = parseFloatSafe(obj.get(14));
        this.calcio = parseFloatSafe(obj.get(15));
        this.magnesio = parseFloatSafe(obj.get(16));
        this.manganes = parseFloatSafe(obj.get(17));
        this.fosforo = parseFloatSafe(obj.get(18));
        this.ferro = parseFloatSafe(obj.get(19));
        this.sodio = parseFloatSafe(obj.get(20));
        this.potassio = parseFloatSafe(obj.get(21));
        this.cobre = parseFloatSafe(obj.get(22));
        this.zinco = parseFloatSafe(obj.get(23));
        this.retinol = parseFloatSafe(obj.get(24));
        this.re = parseFloatSafe(obj.get(25));
        this.rae = parseFloatSafe(obj.get(26));
        this.tiamina = parseFloatSafe(obj.get(27));
        this.riboflavina = parseFloatSafe(obj.get(28));
        this.piridoxina = parseFloatSafe(obj.get(29));
        this.niacina = parseFloatSafe(obj.get(30));
        this.vitamina_c = parseFloatSafe(obj.get(31));
    }

    private float parseFloatSafe (String string){
        if (string == null || string.isEmpty()){
            return 0;
        }
        return parseFloat(string);
    }
}
