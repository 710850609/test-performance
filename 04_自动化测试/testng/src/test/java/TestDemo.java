import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class TestDemo {

    @BeforeTest
    public void before(ITestContext ctx) {
        ctx.setAttribute("name", "bob");
//        Assert.assertTrue(false, "失败1111");
    }

    @Test
    public void aaa(ITestContext ctx) {
        System.out.println(ctx.getAttribute("name"));
        System.out.println(ctx);
        Assert.assertTrue(false, "失败");
//        return "bbb";
    }

    @Test
    public void bbb(ITestContext ctx) {
        System.out.println(ctx.getAttribute("name"));
        System.out.println(ctx);
//        return "bbb";
    }
}
