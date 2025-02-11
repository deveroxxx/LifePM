package bakos.life_pm.service;

import bakos.life_pm.exception.PositionOverflowException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.experimental.UtilityClass;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collections;
import java.util.Map;

import static bakos.life_pm.constant.Constants.SPARSE_POSITION_GAP;

@UtilityClass
public class Utils {

    public static int getSparseOrder(Integer prevPositionValue, Integer nextPositionValue) throws PositionOverflowException {
        int prev = prevPositionValue == null ? 0 : prevPositionValue;
        int next = nextPositionValue == null ? (prev + SPARSE_POSITION_GAP) * 2 : nextPositionValue;

        int newPosition = (prev + next) / 2;
        double precisePosition = (prev + next) / 2.0;

        if (newPosition == (int) precisePosition) {  // this should also handle integer overflow?
            return newPosition;
        } else {
            throw new PositionOverflowException();
        }
    }

    public static String getUserFromSecurityContext() {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth != null && auth.getPrincipal() instanceof UserDetails userDetails) {
                return userDetails.getUsername();
            } else {
                throw new SecurityException("User must be authenticated when this method called!");
            }
    }

    public static String getTokenFromCookie(HttpServletRequest request, String cookieName) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookieName.equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    public static void setCookie(HttpServletResponse response, String name, String value, long maxAge) {
        Cookie cookie = new Cookie(name, value);
        cookie.setHttpOnly(true);
        cookie.setSecure(false); //FIXME: either set localhost https or remove this in prod
        //cookie.setAttribute("SameSite", "None");
        cookie.setPath("/");
        cookie.setMaxAge((int) maxAge);
        response.addCookie(cookie);
    }


    public static Map<String, String> asJson(String text) {
        return Collections.singletonMap("message", text);
    }


}
