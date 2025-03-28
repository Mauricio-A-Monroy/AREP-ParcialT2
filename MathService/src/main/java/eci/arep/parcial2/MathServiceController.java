package eci.arep.parcial2;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
@RestController
public class MathServiceController {
    @GetMapping("/linearsearch")
    public ResponseEntity<?> linearSearchHandler(@RequestParam("list") String list,@RequestParam("value") String value) {

        String[] stringList = list.split(",");
        List<Integer> integerList = new ArrayList<>();
        for(String s: stringList){
            integerList.add(Integer.valueOf(s));
        }

        String ans = linealSearch(integerList, Integer.valueOf(value));

        Map<String, String> jSon = new HashMap<>();

        jSon.put("operation", "linearSearch");
        jSon.put("inputlist", list);
        jSon.put("value", value);
        jSon.put("output", ans);

        System.out.println(jSon.toString());
        return ResponseEntity.status(200).body(jSon);
    }

        @GetMapping("/binarysearch")
    public ResponseEntity<?> binarySearchHandler(@RequestParam("list") String list,@RequestParam("value") String value) {

        String[] stringList = list.split(",");
        List<Integer> integerList = new ArrayList<>();
        for(String s: stringList){
            integerList.add(Integer.valueOf(s));
        }

        Collections.sort(integerList);

        String ans = binarySearch(integerList, Integer.valueOf(value), 0, integerList.size() - 1);

        Map<String, String> jSon = new HashMap<>();

        jSon.put("operation", "binarysearch");
        jSon.put("inputlist", list);
        jSon.put("value", value);
        jSon.put("output", ans);

        System.out.println(jSon.toString());
        return ResponseEntity.status(200).body(jSon);
    }

    private String binarySearch(List<Integer> list, Integer v, Integer l,Integer r) {
        int mid = l + (r - l)/2;

        if (l > r){
            return String.valueOf(-1);
        }

        if(list.get(mid).equals(v)){
            return String.valueOf(mid + 1);
        } else if (list.get(mid) < v){
            return binarySearch(list, v, mid + 1, r);
        } else {
            return binarySearch(list, v, l, mid - 1);
        }
    }

    private String linealSearch(List<Integer> list, Integer v) {
        for (int i = 0; i < list.size(); i++){
            if(list.get(i).equals(v)){
                return String.valueOf(i + 1);
            }
        }
        return String.valueOf(-1);
    }
}