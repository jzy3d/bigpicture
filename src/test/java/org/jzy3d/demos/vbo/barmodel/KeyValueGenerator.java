package org.jzy3d.demos.vbo.barmodel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.jzy3d.demos.vbo.barmodel.model.KeyVal;
import org.jzy3d.demos.vbo.barmodel.model.KeyVal.ValueGenerator;

public class KeyValueGenerator {
    public List<List<KeyVal<String, Float>>> vip(int nPivot, int nPivotCol, int nCpCcCat, int nCpCcCol) {
        List<List<KeyVal<String, Float>>> rows = new ArrayList<List<KeyVal<String, Float>>>(nPivot);

        for (int i = 0; i < nPivot; i++) {
            rows.add(vipRow(nPivot, nPivotCol, nCpCcCat, nCpCcCol));
        }

        return rows;
    }

    public List<KeyVal<String, Float>> vipRow(int nPivot, int nPivotCol, int nCpCcCat, int nCpCcCol) {
        List<KeyVal<String, Float>> row = new ArrayList<KeyVal<String, Float>>();

        row.add(new KeyVal<String, Float>("0.pivot.id", (float) continuousId.value()));

        for (int i = 0; i < nPivotCol; i++) {
            row.add(new KeyVal<String, Float>(i + "pivot.col" + i, randomValue.value()));
        }

        for (int i = 0; i < nCpCcCat; i++) {
            if (Math.random() > 0.33) {
                for (int j = 0; j < nCpCcCol; j++) {
                    row.add(new KeyVal<String, Float>(nPivotCol + "." + i + ".cpcc" + i + ".col" + j, randomValue.value()));
                }
            }
        }

        return row;
    }

    ValueGenerator<String, Float> randomValue = new ValueGenerator<String, Float>() {
        public Float value(String key) {
            return (float) Math.random();
        }

        public Float value() {
            return (float) Math.random();
        }
    };

    ValueGenerator<String, Integer> continuousId = new ValueGenerator<String, Integer>() {
        int n = 0;

        public Integer value(String key) {
            return n++;
        }

        public Integer value() {
            return n++;
        }
    };

    /* */

    @SuppressWarnings("unchecked")
    public void rows7() {
        List<List<KeyVal<String, Float>>> rows = new ArrayList<List<KeyVal<String, Float>>>();
        rows.add(rw(kv("id", 0f), kv("value", 0f), kv("app1.id", 0f), kv("app1.value", 0f)));
        rows.add(rw(kv("id", 1f), kv("value", 0f), kv("app1.id", 0f), kv("app1.value", 10f)));
        rows.add(rw(kv("id", 2f), kv("value", 0f), kv("app1.id", 0f), kv("app1.value", 20f)));
        rows.add(rw(kv("id", 3f), kv("value", 0f), kv("app1.id", 0f), kv("app1.value", 30f)));
        rows.add(rw(kv("id", 4f), kv("value", 0f), kv("app1.id", 0f), kv("app1.value", 40f)));
        rows.add(rw(kv("id", 5f), kv("value", 0f), kv("app1.id", 0f), kv("app1.value", 50f)));
        rows.add(rw(kv("id", 6f), kv("value", 0f), kv("app1.id", 0f), kv("app1.value", 60f)));
    }

    /* */

    static KeyVal<String, Float> kv(String key, Float value) {
        return new KeyVal<String, Float>(key, value);
    }

    static List<KeyVal<String, Float>> rw(KeyVal<String, Float>... keys) {
        List<KeyVal<String, Float>> row = Arrays.asList(keys);// new
                                                              // ArrayList<KeyVal<String,Float>>();
        return row;
    }
}
