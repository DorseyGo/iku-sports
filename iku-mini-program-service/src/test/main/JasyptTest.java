/*
 * File: JasyptTest
 * Author: DorSey Q F TANG
 * Created: 2020/6/30
 * CopyRight: All rights reserved
 */

import com.iku.sports.mini.admin.MiniProgramApplication;
import org.jasypt.encryption.StringEncryptor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {MiniProgramApplication.class})
public class JasyptTest {

    @Autowired
    private StringEncryptor stringEncryptor;

    @Test
    public void testJasypt() {
        System.out.println(stringEncryptor.encrypt("laoxushidaitoudage"));
    }
}
