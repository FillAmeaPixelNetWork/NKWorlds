package anse.Worlds.libs;

import cn.nukkit.Player;
import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.response.FormResponse;
import cn.nukkit.form.response.FormResponseCustom;
import cn.nukkit.form.response.FormResponseModal;
import cn.nukkit.form.response.FormResponseSimple;
import cn.nukkit.form.window.FormWindow;
import com.google.gson.Gson;

import java.util.HashMap;

public class MyForm extends FormWindow {

    protected boolean closed = false;

    private FormWindow window;
    private MyCallable callable;

    public void setWindow(FormWindow window){
        this.window = window;
    }

    public MyForm(FormWindow window, MyCallable callable){
        this.setWindow(window);
        this.callable = callable;
    }

    public void sendToPlayer(Player player){
        player.showFormWindow(this);
    }

    /** 处理数据 */
    public void setResponse(String data){
        if (data.equals("null")) {
            this.closed = true;
        } else {
            this.window.setResponse(data);
            FormResponse response = this.window.getResponse();

            HashMap<Integer, Object> hashMap = new HashMap<>();

            if (response instanceof FormResponseCustom) {
                /**
                 * ElementSlider Float
                 * ElementToggle Boolean
                 * 其他Element都是: String
                 */
                hashMap = ((FormResponseCustom) response).getResponses();
            } else if (response instanceof FormResponseModal) {
                hashMap.put(0, ((FormResponseModal) response).getClickedButtonId());
                hashMap.put(1, ((FormResponseModal) response).getClickedButtonText());
            } else if (response instanceof FormResponseSimple) {
                ElementButton elementButton = ((FormResponseSimple) response).getClickedButton();
                hashMap.put(0, ((FormResponseSimple) response).getClickedButtonId());
                hashMap.put(1, elementButton.getText());
            }

            try {
                this.callable.setHashMap(hashMap);
                this.callable.call();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public FormResponse getResponse(){
        return this.window.getResponse();
    }

    @Override
    public String getJSONData() {
        return (new Gson()).toJson(this.window);
    }

    @Override
    public boolean wasClosed() {
        return this.closed;
    }
}
