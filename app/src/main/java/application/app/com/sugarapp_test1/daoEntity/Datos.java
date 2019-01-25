package application.app.com.sugarapp_test1.daoEntity;

import com.orm.SugarRecord;
import com.orm.dsl.Table;

import java.util.Date;

public class Datos extends SugarRecord {

    private Integer integer;
    private Double real;
    private String text;
    private Date numDate;
    private Boolean numBool;

    public Datos() {
    }

    public Datos(Integer integer, Double real, String text, Date numDate, Boolean numBool) {
        this.integer = integer;
        this.real = real;
        this.text = text;
        this.numDate = numDate;
        this.numBool = numBool;
    }

    public Integer getInteger() {
        return integer;
    }

    public void setInteger(Integer integer) {
        this.integer = integer;
    }

    public Double getReal() {
        return real;
    }

    public void setReal(Double real) {
        this.real = real;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getNumDate() {
        return numDate;
    }

    public void setNumDate(Date numDate) {
        this.numDate = numDate;
    }

    public Boolean getNumBool() {
        return numBool;
    }

    public void setNumBool(Boolean numBool) {
        this.numBool = numBool;
    }

    @Override
    public String toString() {
        return  integer +
                " " + real +
                " " + text + '\'' +
                " " + numDate +
                " " + numBool ;
    }
}
