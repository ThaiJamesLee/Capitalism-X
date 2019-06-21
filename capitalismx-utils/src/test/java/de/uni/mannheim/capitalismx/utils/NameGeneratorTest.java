package de.uni.mannheim.capitalismx.utils;

import de.uni.mannheim.capitalismx.utils.data.PersonMeta;
import de.uni.mannheim.capitalismx.utils.namegenerator.NameGenerator;
import org.junit.Assert;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.testng.annotations.Test;

import java.io.IOException;

public class NameGeneratorTest {

    private String sampleJson = "{\"results\":[{\"gender\":\"female\",\"name\":{\"title\":\"mademoiselle\",\"first\":\"tabea\",\"last\":\"charles\"},\"location\":{\"street\":\"9365 montée du chemin-neuf\",\"city\":\"lauerz\",\"state\":\"thurgau\",\"postcode\":5535,\"coordinates\":{\"latitude\":\"-55.4698\",\"longitude\":\"125.4581\"},\"timezone\":{\"offset\":\"+9:00\",\"description\":\"Tokyo, Seoul, Osaka, Sapporo, Yakutsk\"}},\"email\":\"tabea.charles@example.com\",\"login\":{\"uuid\":\"e22ee27c-22c3-407c-a65b-98a22780d95c\",\"username\":\"sadlion593\",\"password\":\"froggie\",\"salt\":\"jXnaEWlA\",\"md5\":\"5290efb5550c91639d6ee6ed81c44797\",\"sha1\":\"0de1060bb3a712370256d954a9d68c9b729b2e3e\",\"sha256\":\"4537e040e9cdd4670341ede124b23511c4d0752856e1d248015cdb74e4068169\"},\"dob\":{\"date\":\"1978-05-15T23:59:01Z\",\"age\":40},\"registered\":{\"date\":\"2004-11-12T12:40:56Z\",\"age\":14},\"phone\":\"(086)-374-8842\",\"cell\":\"(947)-526-7827\",\"id\":{\"name\":\"AVS\",\"value\":\"756.8111.9522.91\"},\"picture\":{\"large\":\"https://randomuser.me/api/portraits/women/15.jpg\",\"medium\":\"https://randomuser.me/api/portraits/med/women/15.jpg\",\"thumbnail\":\"https://randomuser.me/api/portraits/thumb/women/15.jpg\"},\"nat\":\"CH\"}],\"info\":{\"seed\":\"ef6ee698ab0c4b60\",\"results\":1,\"page\":1,\"version\":\"1.2\"}}";

    @Test
    public void getGeneratedPersonMetaTest () {
       
    }
}
