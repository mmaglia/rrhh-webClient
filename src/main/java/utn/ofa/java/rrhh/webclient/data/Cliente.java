package utn.ofa.java.rrhh.webclient.data;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import org.json.JSONException;
import org.json.JSONObject;

@Entity
public class Cliente implements Serializable {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    Integer id;
    String nombre;
    String correo;
    String cuit;
    @OneToMany(mappedBy = "cliente")
    private List<Proyecto> proyectos;

   public Cliente() {}

   public Cliente(String JSONEntity) throws JSONException {
       JSONObject obj = new JSONObject(JSONEntity);
       this.id = obj.getInt("id");
       this.nombre = obj.getString("nombre");
       this.correo = obj.getString("correo");
       this.cuit = obj.getString("cuit");
   }
   
    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCuit(String cuit) {
        this.cuit = cuit;
    }

    public String getCuit() {
        return cuit;
    }
    
    @Override
    public String toString() {
        return this.nombre;
    }
    
    public String toJSON() throws JSONException {
        JSONObject obj = new JSONObject();
        obj.put("id", this.id);
        obj.put("nombre", this.nombre);
        obj.put("correo", this.correo);
        obj.put("cuit", this.cuit);
        return obj.toString();
    }
}
