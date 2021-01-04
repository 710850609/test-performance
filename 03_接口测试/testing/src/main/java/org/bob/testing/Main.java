package org.bob.testing;

import org.bob.testing.request.ExecuteRunner;
import org.bob.testing.request.RequestBuilder;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        // 登录相关测试单元
        List<RequestBuilder> requests = new ArrayList<>();
        // 登录请求
        requests.add(LoginUnit.login());
        // 登出请求
        requests.add(LoginUnit.logout());

        ExecuteRunner runner = ExecuteRunner.build()
                // 添加请求
                .addRequests(requests)
                // 出现异常不中断
                .stopForError(true)
                // 启动线程个数
                .threadNum(4)
                // 单个线程循环次数
                .threadCycleNum(1);
        // 启动测试
        runner.start();
    }
}
