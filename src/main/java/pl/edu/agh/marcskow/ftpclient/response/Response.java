package pl.edu.agh.marcskow.ftpclient.response;


import lombok.Data;

@Data
public class Response {
    private int intCode;
    private int length;
    private String code;
    private String body;
    private String[] elements = null;

    public String getElement(int i){
        if(elements.length <= i){
            return "";
        }
        else{
            return elements[i];
        }
    }
}
