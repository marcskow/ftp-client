package pl.edu.agh.marcskow.ftpclient.response;


import java.util.Arrays;

public class Parser {
    public static Response parse(String response){
        Response result = new Response();

        String[] elements = response.split(" ");

        if(elements.length > 1) {
            result.setCode(elements[0]);
            result.setLength(elements.length - 1);
            result.setIntCode(Integer.parseInt(elements[0]));
            result.setBody(response.substring(4,response.length() - 1));
            result.setElements(Arrays.copyOfRange(elements,1,elements.length + 1));
        }

        return result;
    }
}
