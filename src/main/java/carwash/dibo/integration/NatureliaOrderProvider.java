package carwash.dibo.integration;

import carwash.dibo.utils.ParamsHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class NatureliaOrderProvider implements NatureliaOrderService {

    private final static String DEFAULT_AUTH_LINK = "https://naturelia.net/login/";
    private final static String AUTH_LINK_CHECKER = "https://naturelia.net/account/";
    private final static String RE_ORDER_LINK = "https://naturelia.net/index.php?route=account/order/reorderall&";
    private final static String PLACE_ORDER_LINK = "https://naturelia.net/index.php?route=account/order/info&order_id=";
    private final static String ORDER_ID = "18325";
    private final static String PRODUCT_ID = "29696";
    private final static String PRODUCT_CODE = "68336";

    private final String LOGIN_EMAIL;
    private final String PASSWORD;

    private List<String> cookies;

    public NatureliaOrderProvider(@Value("${naturelia.username}") String login_email,
                                  @Value(value = "${naturelia.password}") String password) {
        LOGIN_EMAIL = login_email;
        PASSWORD = password;
    }

    private void setCookies(List<String> cookies) {
        this.cookies = cookies;
    }

    @Override
    public ResponseEntity<String> placeOrder() {
        ResponseEntity<String> authResponse = doAuth();
        if (!authResponse.getStatusCode().equals(HttpStatus.OK)) {
            return authResponse;
        }

        ResponseEntity<String> addToCartResponse = addWaterToCart();
        if (!addToCartResponse.getStatusCode().equals(HttpStatus.OK)){
            return addToCartResponse;
        }

        return confirmOrder();
    }

    private ResponseEntity<String> doAuth() {
        try {
            ResponseEntity<String> response = new RestTemplate()
                    .postForEntity(DEFAULT_AUTH_LINK, getEntityWithHeaders(LOGIN_EMAIL, PASSWORD), String.class);
            setCookies(response.getHeaders().get("Set-Cookie"));
        } catch (Exception e) {
            return new ResponseEntity<>("Auth error!, Please try again", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (isAuthorized()) {
            return new ResponseEntity<>("Login success", HttpStatus.OK);
        } else return new ResponseEntity<>("Authorization failed", HttpStatus.FORBIDDEN);
    }

    private boolean isAuthorized() {
        if (cookies == null && cookies.isEmpty()) {
            return false;
        }

        ResponseEntity<String> htmlText = new RestTemplate().exchange(
                AUTH_LINK_CHECKER,
                HttpMethod.GET,
                getEntityWithCookie(cookies),
                String.class);

        return htmlText.toString().contains("Мои заказы");
    }

    private ResponseEntity<String> addWaterToCart() {
        String url = RE_ORDER_LINK + ORDER_ID;

        try {
            new RestTemplate().postForEntity(url, getEntityWithCookie(cookies), String.class);
        } catch (Exception e) {
            return new ResponseEntity<>("Can't add water to cart", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>("Success, water added to cart", HttpStatus.OK);
    }

    private ResponseEntity<String> confirmOrder() {
        String urlEncoded = ParamsHelper.httpBuildQuery(getOrderParams(), "UTF-8");
        String url = PLACE_ORDER_LINK + urlEncoded;

        try {
            new RestTemplate().postForEntity(url, getEntityWithCookie(cookies), String.class);

        } catch (Exception ex) {
            return new ResponseEntity<>("Cant't place order", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>("Success, order have been placed", HttpStatus.OK);

    }

    private HttpEntity<MultiValueMap<String, String>> getEntityWithHeaders(String email, String password) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("email", email);
        body.add("password", password);

        return new HttpEntity<>(body, headers);
    }

    private HttpEntity<MultiValueMap<String, String>> getEntityWithCookie(List<String> cookie) {
        HttpHeaders headers = new HttpHeaders();
        headers.addAll("Cookie", cookie);
        return new HttpEntity<>(null, headers);
    }

    public Map<String, Object> getLastOrderParams() {
        Map<String, Object> params = new HashMap<>();
        params.put("order_id", ORDER_ID);
        params.put("order_product_id", PRODUCT_ID);

        return params;
    }

    private Map<String, Object> getOrderParams() {

        Map<String, Object> params = new HashMap<>();
        String shippingField = "shipping_address";

        params.put("quantity" + "[" + PRODUCT_CODE + "]", 2);
        params.put("remove", "");
        params.put("customer[telephone]", "+79999999");
        params.put(shippingField + "[address_id]", "3882");
        params.put(shippingField + "[firstname]", "Anton");
        params.put(shippingField + "[lastname]", "Petrov");
        params.put(shippingField + "[company]", "karakas");
        params.put(shippingField + "[city]", "Saint-Petersburg");
        params.put(shippingField + "[field20]", "");
        params.put(shippingField + "[country_id]", 176);
        params.put(shippingField + "[zone_id]", "2785");
        params.put(shippingField + "[postcode]", "199999");
        params.put(shippingField + "[current_address_id]", "3882");
        params.put("shipping_method", "free.free");
        params.put("shipping_method_current", "free.free");
        params.put("shipping_method_checked", "");
        params.put("payment_method", "cod");
        params.put("payment_method_current", "cod");
        params.put("payment_method_checked", "");
        params.put("comment", "");
        params.put("agreements[]", "all");
        params.put("next_step", 1);
        params.put("simple_ajax", 1);

        return params;
    }
}














