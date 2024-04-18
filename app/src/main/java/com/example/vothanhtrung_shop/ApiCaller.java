package com.example.vothanhtrung_shop;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.mindrot.jbcrypt.BCrypt;

import java.util.HashMap;
import java.util.Map;

public class ApiCaller {
    private RequestQueue requestQueue;
    private static ApiCaller instance;
    private static Context ctx;
    public static String url = "http://192.168.1.10:8080/api";

    private ApiCaller(Context context) {
        ctx = context.getApplicationContext();

        requestQueue = getRequestQueue();
    }

    public static synchronized ApiCaller getInstance(Context context) {
        if (instance == null) {
            instance = new ApiCaller(context);
        }
        return instance;
    }

    private RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(ctx);
        }
        return requestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }

    public void makeStringRequest(String url, final
    ApiResponseListener<String> listener) {
        StringRequest stringRequest = new
                StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override

                    public void onResponse(String response) {
                        try {
                            String utf8Response = new
                                    String(response.getBytes("ISO-8859-1"), "UTF-8");
                            listener.onSuccess(utf8Response);
                        } catch (Exception e) {
                            listener.onError(e.getMessage());
                        }
                    }
                }, new Response.ErrorListener() {
            @Override

            public void onErrorResponse(VolleyError error) {
                listener.onError(error.getMessage());
            }
        });
        addToRequestQueue(stringRequest);
    }

    public void makeJsonArrayRequest(String url, final
    ApiResponseListener<JSONArray> listener) {
        JsonArrayRequest jsonArrayRequest = new
                JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override

                    public void onResponse(JSONArray response) {

                        listener.onSuccess(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onError(error.getMessage());
            }
        });
        addToRequestQueue(jsonArrayRequest);
    }

    public interface ApiResponseListener<T> {
        void onSuccess(T response);

        void onError(String errorMessage);
    }

    public JSONArray stringToJsonArray(String jsonString) {
        JSONArray jsonArray = null;
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            jsonArray = jsonObject.getJSONArray("content");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonArray;
    }

    public void addUser(User user, final ApiResponseListener<User> listener) {
        String addUserUrl = url + "/users/adduser";


        StringRequest request = new StringRequest(Request.Method.POST, addUserUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    User savedUser = user;
                    listener.onSuccess(savedUser);

                } catch (JSONException e) {
                    listener.onError("Errror parsing sever resonse");

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onError("Error adding user");
                System.out.print(error.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("username", user.getUsername());
                params.put("pass", BCrypt.hashpw(user.getPass(), BCrypt.gensalt()));
                params.put("email", user.getEmail());
                params.put("numphone", user.getNumphone());
                params.put("photo", user.getPhoto());
                return params;

            }
        };
        addToRequestQueue(request);
    }

    public void updateUser(User user, final ApiResponseListener<User>
            listener) {
        String addUserUrl = url + "/users/" + User.getId();
        StringRequest request = new StringRequest(Request.Method.PUT,
                addUserUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new
                                    JSONObject(response);

                            User savedUser = user;
                            listener.onSuccess(savedUser);
                        } catch (JSONException e) {
                            listener.onError("Error parsing server response.");
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onError("Error adding user.");
                System.out.print(error.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("username", user.getUsername());
                params.put("email", user.getEmail());
                params.put("numphone", user.getNumphone());
                params.put("photo", user.getPhoto());
                params.put("pass", BCrypt.hashpw(user.getPass(),
                        BCrypt.gensalt()));
                return params;
            }
        };
        addToRequestQueue(request);
    }

    public void addCart(final int userId, final
    ApiResponseListener<JSONObject> listener) {
        String cartUrl = url + "/carts"; // Đường dẫn API để tạo cart
        JSONObject cartJson = new JSONObject();
        try {
            JSONObject userJson = new JSONObject();
            userJson.put("id", userId);
            cartJson.put("user", userJson);
        } catch (JSONException e) {
            e.printStackTrace();
            listener.onError(e.getMessage());
            return;
        }
        JsonObjectRequest jsonObjectRequest = new
                JsonObjectRequest(Request.Method.POST, cartUrl, cartJson,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        listener.onSuccess(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onError(error.getMessage());
            }
        });
        addToRequestQueue(jsonObjectRequest);
    }

    public void deleteCart(int cartId, final ApiResponseListener<String>
            listener) {
        String deleteCartUrl = url + "/carts/" + cartId;
        StringRequest request = new StringRequest(Request.Method.DELETE,
                deleteCartUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Assuming successful deletion returns a message
                        listener.onSuccess("Cart successfully deleted!");
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onError("Error deleting cart.");
                Log.e("deleteCartError", error.toString());
            }
        });
        addToRequestQueue(request);
    }

    public void addCartDetail(final int cartId, final int productId,
                              final int quantity, final ApiResponseListener<JSONObject> listener) {
        String cartDetailUrl = url + "/cartDetails";
        JSONObject cartDetailJson = new JSONObject();
        try {
            JSONObject productJson = new JSONObject();
            productJson.put("id", productId);
            JSONObject cartJson = new JSONObject();
            cartJson.put("id", cartId);
            cartDetailJson.put("quantity", quantity);
            cartDetailJson.put("product", productJson);
            cartDetailJson.put("cart", cartJson);
        } catch (JSONException e) {
            e.printStackTrace();
            listener.onError(e.getMessage());
            return;
        }
        JsonObjectRequest jsonObjectRequest = new
                JsonObjectRequest(Request.Method.POST, cartDetailUrl, cartDetailJson,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        listener.onSuccess(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onError(error.getMessage());
            }
        });
        addToRequestQueue(jsonObjectRequest);
    }
    public void updateCartDetail(final int cartId, final int productId, final int quantity, final ApiResponseListener<JSONObject> listener) {
        String cartDetailUrl = url + "/cartDetails";
        JSONObject cartDetailJson = new JSONObject();
        try {
            JSONObject productJson = new JSONObject();
            productJson.put("id", productId);
            JSONObject cartJson = new JSONObject();
            cartJson.put("id", cartId);
            cartDetailJson.put("quantity", quantity);
            cartDetailJson.put("product", productJson);
            cartDetailJson.put("cart", cartJson);
        } catch (JSONException e) {
            e.printStackTrace();
            listener.onError(e.getMessage());
            return;
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, cartDetailUrl, cartDetailJson,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        listener.onSuccess(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onError(error.getMessage());
            }
        });
        addToRequestQueue(jsonObjectRequest);
    }


    public void deleteItemCart(int cartItemId, final
    ApiResponseListener<String> listener) {
        String deleteCartUrl = url + "/cartDetails/" + cartItemId;
        Log.d("ddddlete", deleteCartUrl);
        StringRequest request = new StringRequest(Request.Method.DELETE,
                deleteCartUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Assuming successful deletion returns a message
                        listener.onSuccess("Cart successfully deleted!");
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onError("Error deleting cart.");
                Log.e("deleteCartError", error.toString());
            }
        });
        addToRequestQueue(request);
    }

    public void addOrder(final Order order, final
    ApiResponseListener<JSONObject> listener) {
        String cartUrl = url + "/orders"; // Đường dẫn API để tạo cart
        JSONObject ordertJson = new JSONObject();
        try {
            JSONObject userJson = new JSONObject();
            userJson.put("id", order.getIduser());
            ordertJson.put("user", userJson);
            ordertJson.put("date", order.getDate());
        } catch (JSONException e) {
            e.printStackTrace();
            listener.onError(e.getMessage());
            return;
        }
        JsonObjectRequest jsonObjectRequest = new
                JsonObjectRequest(Request.Method.POST, cartUrl, ordertJson,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        listener.onSuccess(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onError(error.getMessage());
            }
        });
        addToRequestQueue(jsonObjectRequest);
    }

    public void addOrderDetail(final OrderDetail orderDetail, final
    ApiResponseListener<JSONObject> listener) {
        String cartUrl = url + "/orderDetails";
        JSONObject orderDetailtJson = new JSONObject();
        try {
            JSONObject orderJson = new JSONObject();
            orderJson.put("id", orderDetail.getOrderid());
            orderDetailtJson.put("order", orderJson);
            JSONObject productJson = new JSONObject();
            productJson.put("id", orderDetail.getProductid());
            orderDetailtJson.put("product", productJson);
            orderDetailtJson.put("quantity", orderDetail.getQuantity());
        } catch (JSONException e) {
            e.printStackTrace();
            listener.onError(e.getMessage());
            return;
        }
        JsonObjectRequest jsonObjectRequest = new
                JsonObjectRequest(Request.Method.POST, cartUrl, orderDetailtJson,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        listener.onSuccess(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onError(error.getMessage());
            }
        });
        addToRequestQueue(jsonObjectRequest);
    }
}