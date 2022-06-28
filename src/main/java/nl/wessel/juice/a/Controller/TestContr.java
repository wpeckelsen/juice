package nl.wessel.juice.a.Controller;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
public class TestContr {
    @GetMapping("/test")
    public ResponseEntity<String> test(){
        return ResponseEntity.ok().body("Hello world, test test test" + "username: user, password: on command line");
    }
}
