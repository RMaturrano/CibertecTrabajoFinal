package com.project.cibertec.finalproject.pedido;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Created by PC on 09/06/2016.
 */
public class DoubleRound {

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

}
