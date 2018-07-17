
package org.gz.app.test;

import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class) // SpringJUnit支持，由此引入Spring-Test框架支持！ 
public class ProviderUserTest {
   /* private TechUtil techUtil = new TechUtil();
    private String srcPdfFile = "G:/data/unsign.pdf";
    private String url = "";
	@Test
	public void testSignForNew(){
	    AddAccountResult accountResult = techUtil.addAccount("罗芷蓝", "330522197512254121", "18503072015");
	    AddSealResult addSealResult = techUtil.addTemplateSeal(accountResult.getAccountId(), PersonTemplateType.YYGXSF, SealColor.RED);
	    String srcPdfFile = "G:/data/unsign.pdf";
	    StringBuffer dstPdfFile = new StringBuffer("G:/data/test/");
	    dstPdfFile.append(UUID.randomUUID().toString());
	    dstPdfFile.append(".pdf");
	    String code = techUtil.sendMessageOfSign(accountResult.getAccountId());
	    techUtil.sign(accountResult.getAccountId(),addSealResult.getSealData(),srcPdfFile, dstPdfFile.toString(),code);
	}
	
	@Test
    public void testSign(){
	    String accountId = "E483508D69EC48D7A99A64C94F9A4DE9";
        AddSealResult addSealResult = techUtil.addTemplateSeal(accountId, PersonTemplateType.YYGXSF, SealColor.RED);
        String srcPdfFile = "G:/data/unsign.pdf";
        StringBuffer dstPdfFile = new StringBuffer("G:/data/test/");
        dstPdfFile.append(UUID.randomUUID().toString());
        dstPdfFile.append(".pdf");
        //String code = techUtil.sendMessageOfSign(accountResult.getAccountId());
        techUtil.sign(accountId,addSealResult.getSealData(),srcPdfFile, dstPdfFile.toString(),"462116");
    }
	@Test
	public void testSendMessage(){
	    //String code = techUtil.sendMessageOfSign(accountResult.getAccountId());
	    System.out.println("*************************");
	}
	
	@Test
    public void testUpdateAccount(){
	    UpdatePersonBean person = new UpdatePersonBean();
	    person.setMobile("18503072015");
	    techUtil.updateAccount("E483508D69EC48D7A99A64C94F9A4DE9", person, null);
        //String code = techUtil.sendMessageOfSign(accountResult.getAccountId());
    }
	@Test
	public void testPreserveUrl(){
	    try {
            byte[] bytes = Files.readAllBytes(Paths.get(srcPdfFile));
            PreserveEntity preserveEntity = new PreserveEntity();
            int endIndex = srcPdfFile.indexOf(".pdf");
            int beginIndex = srcPdfFile.lastIndexOf("/");
            LocalDate storageLife = LocalDate.of(2017, 12, 31);
            preserveEntity.setStorageLife(storageLife);
            preserveEntity.setContentLength(String.valueOf(bytes.length));
           // String contentBase64Md5 = EncryptionUtil.getStringContentMD5(new String(bytes));
            String contentBase64Md5 = AlgorithmHelper.getContentMD5(srcPdfFile);
            preserveEntity.setContentBase64Md5(contentBase64Md5);
            preserveEntity.setContentDescription(srcPdfFile.substring(beginIndex+1,endIndex+4));
            
            TypeEntity typeEntity = new TypeEntity();
            typeEntity.setType("0");
            typeEntity.setValue("940763479881584647");
            List<TypeEntity> eSignIds = new ArrayList<>();
            eSignIds.add(typeEntity);
            
            PreserveResultEntity result = techUtil.getPreserveUrl("存证测试", preserveEntity, eSignIds, null);
            url = result.getUrl();
            System.out.println(result.getEvid());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
	   
	}
	
	@Test
    public void testUploadFile() throws Exception{
        techUtil.uploadFile(url, srcPdfFile);
	}
	@Test
	public void addOrganize(){
	    String name = "深圳市国智网络科技有限公司";
	    String organCode = "MA5DPHR10";
	    AddAccountResult accountResult = techUtil.addOrganize(name, organCode);
	    System.out.println(JSON.toJSONString(accountResult));
	}
	@Before
    public void testBefore(){
	   testPreserveUrl();
    }
	@Test
	public void testMd5(){
	    String str = AlgorithmHelper.getContentMD5(srcPdfFile);
	    System.out.println(str);
	}
	@Test
	public void testRelate(){
	    CertificateEntity org = new CertificateEntity();
	    org.setNumber("MA5DPHR10");
	    org.setName("深圳市国智网络科技有限公司");
	    org.setType("CODE_ORG");
	    List<CertificateEntity> list = new ArrayList<>();
	    list.add(org);
	    
	    CertificateEntity person = new CertificateEntity();
	    person.setName("罗芷蓝");
	    person.setNumber("330522197512254121");
	    person.setType("ID_CARD");
	    list.add(person);
	    
	    String evid = "O941570534469038083";
	    ResultDto resultDto = techUtil.relate(evid, list);
	    System.out.println(JSON.toJSONString(resultDto));
	}*/
}
