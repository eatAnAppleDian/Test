import com.nimbusds.jose.JOSEException;
import com.orange.SpringBootApplicationStart;
import com.orange.security.entity.TokenPayLoad;
import com.orange.security.util.JwtTokenUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.ParseException;

@SpringBootTest(classes = SpringBootApplicationStart.class)
public class JwtTest {
    @Test
    public void testJwt() throws JOSEException, ParseException {
        String userName = "admin";
        String token = JwtTokenUtil.generateTokenByRSA(userName);

        TokenPayLoad tokenPayLoad = JwtTokenUtil.verifyTokenByRSA(token);
        System.out.println(tokenPayLoad);
    }
}