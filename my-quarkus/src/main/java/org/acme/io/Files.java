package org.acme.io;

import java.io.IOException;
import java.nio.file.Path;

import java.security.AccessController;
import java.security.PrivilegedAction;

@SuppressWarnings("removal")
public class Files {

    // 这段代码是热点代码，所以用native-rust重写，用于读取文件。
    // 虽然在 java 标准库里面有类似的工具类，但是他不够快，且由于jvm执行的开销相对太过大。
    // 所以这个用Rust重写，使用零拷贝技术mmap以解决热点性能问题。
    // 有比mmap更快的零拷贝技术sendfile，但是后者需要socket_fd，java接口没有暴露底层的socket_fd，因此暂时无法实现。
    public native static String readString(Path path) throws IOException;

    // 加载类 libreadstring，这个动态链接库是用 Rust 写的
    static {
        AccessController.doPrivileged(new PrivilegedAction<Void>() {
            public Void run() {
                System.loadLibrary("libreadstring");
                return null;
            }});
    }
}
