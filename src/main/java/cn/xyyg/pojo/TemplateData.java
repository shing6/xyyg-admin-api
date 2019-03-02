package cn.xyyg.pojo;

import java.math.BigDecimal;

public class TemplateData {

    private String key;

    private String value;

    private String color;

	private BigDecimal value2;

	private String color2;

 

    public TemplateData(String value, String color) {

        this.value = value;

        this.color = color;

    }



	public TemplateData(BigDecimal value2, String color2) {
		 this.value2 = value2;

	     this.color2 = color2;
	}

    
      
	public BigDecimal getValue2() {
		return value2;
	}



	public void setValue2(BigDecimal value2) {
		this.value2 = value2;
	}



	public String getColor2() {
		return color2;
	}



	public void setColor2(String color2) {
		this.color2 = color2;
	}



	public String getKey() {
		return key;
	}
 


	public void setKey(String key) {
		this.key = key;
	}



	public String getValue() {
		return value;
	}



	public void setValue(String value) {
		this.value = value;
	}



	public String getColor() {
		return color;
	}



	public void setColor(String color) {
		this.color = color;
	}

}
