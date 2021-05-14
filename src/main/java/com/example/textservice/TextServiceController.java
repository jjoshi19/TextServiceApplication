package com.example.textservice;



import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
public class TextServiceController {
    @GetMapping("/camelize")
    public String getCamelize(@RequestParam(required = true) String original,
                              @RequestParam(required = false) boolean initialCap){
       StringBuilder resultString = new StringBuilder();
       String[] words= original.split("_");
        for(String temp:words){
            resultString.append(Character.toUpperCase(temp.charAt(0)) + temp.substring(1));
        }
        if(!initialCap){
            resultString.setCharAt(0,Character.toLowerCase(resultString.charAt(0)));
        }
        return resultString.toString();
    }

    @GetMapping("/redact")
    public String getRedact(@RequestParam String original, @RequestParam(value="badWord") List<String> badWords ){
        for(String badWord: badWords){
            String replaceStr =repeatStr(badWord);
            original = original.replaceAll(badWord, replaceStr);
        }
        return original;
    }

    private  String repeatStr(String source){
        StringBuilder builder = new StringBuilder();
        for(int i=0;i< source.length();i++){
            builder.append("*");
        }
        return builder.toString();
    }
    @PostMapping("/s/{find}/{replacement}")
    public String findAndReplace(@RequestBody String s, @PathVariable String find, @PathVariable String replacement ){

        return s.replaceAll(find, replacement);

    }

    @PostMapping("/encode")
    public String encode(@RequestParam String message, @RequestParam String key ){
        StringBuilder resultString = new StringBuilder();
        if(key!= null && !key.isBlank() && key.length() ==26){
            char[] mappingSource = "abcdefghijklmnopqrstuvwzyz".toCharArray();
            HashMap<Character, Character> map = new HashMap<>();
            for(int i =0; i<mappingSource.length; i++){
                map.put(mappingSource[i], key.charAt(i) );
            }
            for(char c:message.toCharArray()){
                if(c==' ')
                    resultString.append(' ');
                else
                    resultString.append(map.get(c));
            }
        }

        return resultString.toString();

    }

}
