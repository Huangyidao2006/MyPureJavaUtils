package test;

import com.hj.net.UdpHelper;

import java.util.Scanner;

public class UdpHelperTest {
    private UdpHelper mSendHelper;
    private UdpHelper mRecvHelper;

    private UdpHelper.UdpListener mListener1 = new UdpHelper.UdpListener() {
        @Override
        public void onRecv(byte[] data, String ip, int port) {
            String buffer = new String(data, 0, data.length);

            System.out.println("echo=" + buffer + ", ip=" + ip + ", port=" + port);
        }
    };

    private UdpHelper.UdpListener mListener2 = new UdpHelper.UdpListener() {
        @Override
        public void onRecv(byte[] data, String ip, int port) {
            String buffer = new String(data, 0, data.length);

            System.out.println("recv=" + buffer + ", ip=" + ip + ", port=" + port);

            if (mRecvHelper != null) {
                mRecvHelper.send(data, ip, port);
            }
        }
    };

    private static final int SEND_PORT = 1234;
    private static final int RECV_PORT = 2345;

    public void test() {
        mSendHelper = new UdpHelper(SEND_PORT);
        mSendHelper.setUdpListener(mListener1);

        mRecvHelper = new UdpHelper(RECV_PORT);
        mRecvHelper.setUdpListener(mListener2);

        while (true) {
            Scanner scanner = new Scanner(System.in);
            String input = scanner.next();

            if ("e".equals(input)) {
                System.out.println("time=" + System.currentTimeMillis());
                break;
            }

            int ret = mSendHelper.send(input.getBytes(), "192.168.31.255", RECV_PORT);

            System.out.println("send=" + input + ", ret=" + ret);
        }

        mSendHelper.destroy();
        mRecvHelper.destroy();
    }
}
