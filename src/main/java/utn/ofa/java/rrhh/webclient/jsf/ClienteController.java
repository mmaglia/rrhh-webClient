package utn.ofa.java.rrhh.webclient.jsf;

import utn.ofa.java.rrhh.webclient.data.Cliente;
import utn.ofa.java.rrhh.webclient.jsf.util.JsfUtil;
import utn.ofa.java.rrhh.webclient.jsf.util.JsfUtil.PersistAction;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.json.JSONArray;
import org.json.JSONException;

@Named("clienteController")
@SessionScoped
public class ClienteController implements Serializable {

    private WebTarget webTarget;
    private Client client;
    private static final String BASE_URI = "http://localhost:8080/rrhh-web/api";

    private List<Cliente> items = null;
    private Cliente selected;

    public ClienteController() {
        client = ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path("cliente");
    }

    public Cliente getSelected() {
        return selected;
    }

    public void setSelected(Cliente selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    public Cliente prepareCreate() {
        selected = new Cliente();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Cliente").getString("ClienteCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Cliente").getString("ClienteUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Cliente").getString("ClienteDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Cliente> getItems() {
        if (items == null) {
            items = findAll();
        }
        return items;
    }

    private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            setEmbeddableKeys();
            try {
                if (persistAction == PersistAction.DELETE) {
                    remove(selected);
                } else if (persistAction == PersistAction.UPDATE) {
                    edit(selected);
                } else {
                    createEntity(selected);

                }
                JsfUtil.addSuccessMessage(successMessage);
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Cliente").getString("PersistenceErrorOccured"));
            }
        }
    }

    public Cliente getCliente(java.lang.Integer id) {
        try {
            WebTarget resource = webTarget;
            resource.path(MessageFormat.format("{0}", new Object[]{id}));
            String jsonCliente = resource.request(MediaType.APPLICATION_JSON).get(String.class);
            return new Cliente(jsonCliente);
        } catch (JSONException e) {
            JsfUtil.addErrorMessage("No se pudo encontrar el Cliente ID " + id);
            return null;
        }
    }
    
    private void edit(Cliente entity) throws Exception {
        // The RESTFul service returns a response that indicates whether or not the update was successful
        Response response = webTarget.path(java.text.MessageFormat.format("{0}", new Object[]{entity.getId() + ""})).request(MediaType.APPLICATION_JSON)
                .put(javax.ws.rs.client.Entity.entity(entity, javax.ws.rs.core.MediaType.APPLICATION_JSON), Response.class);
        
        // If the response status is not OK, throw an exception
        if (response.getStatus() != Response.Status.OK.getStatusCode()) {
            throw new Exception("No es posible actualizar el Cliente ID " + entity.getId() + ". Código de Error: " + response.getStatus());
        }
    }

    private void createEntity(Cliente entity) throws Exception {
        // The RESFul service returns a response that indicates whether or not the create was successful
        Response response = webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_JSON)
                .post(javax.ws.rs.client.Entity.entity(entity, javax.ws.rs.core.MediaType.APPLICATION_JSON), Response.class);
        // If the response status is not OK, throw an exception
        if (response.getStatus() != Response.Status.OK.getStatusCode()) {
            throw new Exception("No es posible crear el Cliente. Código de Error: " + response.getStatus());
        }
    }

    private void remove(Cliente c) {
        // Server request expected id to be a text string
        String idString = c.getId() + "";
        WebTarget resource = webTarget;
        resource.path(java.text.MessageFormat.format("{0}", new Object[]{idString})).request().delete();
    }

    public List<Cliente> getItemsAvailableSelectMany() {
        return findAll();
    }

    public List<Cliente> getItemsAvailableSelectOne() {
        return findAll();
    }

    public List<Cliente> findAll() {
        try {
            WebTarget resource = webTarget;
            String jsonListString = resource.request(MediaType.APPLICATION_JSON).get(String.class);
            return converToList(jsonListString);
        } catch (Exception e) {
            JsfUtil.addErrorMessage("Excepción ocurrida al intentar recuperar un rango de Clientes");
            return new ArrayList();
        }
    }

    public List<Cliente> converToList(String jsonListString) throws JSONException {
        try {
            JSONArray jsonList = new JSONArray(jsonListString);
            List<Cliente> list = new ArrayList();
            for (int i = 0; i < jsonList.length(); i++) {
                Cliente anCliente = new Cliente(jsonList.get(i).toString());
                list.add(anCliente);
            }
            return list;
        } catch (Exception e) {
            throw new JSONException(e.getMessage());
        }
    }

    @FacesConverter(forClass = Cliente.class)
    public static class ClienteControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ClienteController controller = (ClienteController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "clienteController");
            return controller.getCliente(getKey(value));
        }

        java.lang.Integer getKey(String value) {
            java.lang.Integer key;
            key = Integer.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Integer value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Cliente) {
                Cliente o = (Cliente) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Cliente.class.getName()});
                return null;
            }
        }

    }

}
