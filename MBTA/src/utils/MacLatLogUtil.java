package utils;

import json.MacBean;
import com.google.gson.Gson;
import main.Application;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import json.LocationBean;

/**
 *
 * @author Eddy
 */
public class MacLatLogUtil {
    
    //Decode the location object returned by Google API
    public static LocationBean LatLog() throws UnknownHostException, Exception {
        
        InetAddress ia = InetAddress.getLocalHost();

        MacBean Mac = new MacBean(formatMACAddress(ia));

        String latlog = JsonPostUtil.MACpostJson(Application.googleAPI, Mac);

        Gson gson = new Gson();
        LocationBean item = gson.fromJson(latlog, LocationBean.class);

        return item;
    }
    
    //Format the MAC address from Calling System Function
    public static String formatMACAddress(InetAddress ia) throws Exception {
        byte[] mac = NetworkInterface.getByInetAddress(ia).getHardwareAddress();

        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < mac.length; i++) {
            if (i != 0) {
                sb.append("-");
            }
            String s = Integer.toHexString(mac[i] & 0xFF);
            sb.append(s.length() == 1 ? 0 + s : s);
        }
        return sb.toString().toUpperCase();
    }
}
