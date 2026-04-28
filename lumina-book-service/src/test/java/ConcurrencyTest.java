import org.springframework.web.client.RestTemplate;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ConcurrencyTest {

    public static void main(String[] args) throws InterruptedException {
        // 1. 设置并发人数（假设 50 个人同时抢这一本书）
        int threadCount = 50;

        // 2. 创建一个线程池，容纳这 50 个人
        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);

        // 3. 创建发令枪（参数 1 代表只需要响一声枪，大家就一起跑）
        CountDownLatch latch = new CountDownLatch(1);

        // 发送 HTTP 请求的工具
        RestTemplate restTemplate = new RestTemplate();

        System.out.println("🏃‍♂️ 50 名用户正在疯狂点击鼠标，等待网络传输...");

        for (int i = 0; i < threadCount; i++) {
            final String username = "抢书狂魔_" + i;

            executorService.submit(() -> {
                try {
                    // 【核心】所有线程走到这里都会被阻塞，卡在起跑线上等发令枪响
                    latch.await();

                    // 🎯 注意：这里替换成你 book-service 真实的端口号（比如 8080 或 8081）
                    // 我们都在抢 bookId = 1 的这本书
                    String url = "http://localhost:8082/book/borrow?bookId=5&username=" + username;

                    // 发起 POST 请求
                    String result = restTemplate.postForObject(url, null, String.class);
                    System.out.println("🏁 " + username + " 抢书结果: " + result);

                } catch (Exception e) {
                    System.out.println("❌ " + username + " 请求失败: " + e.getMessage());
                }
            });
        }

        // 故意让主线程等 2 秒，确保上面 50 个线程都已创建完毕并在起跑线卡好位置
        Thread.sleep(2000);

        System.out.println("\n🔫 砰！发令枪响，50 个并发瞬间爆发！\n");

        // 4. 扣动发令枪！唤醒所有被 latch.await() 阻塞的线程
        latch.countDown();

        // 关闭线程池
        executorService.shutdown();
    }
}
