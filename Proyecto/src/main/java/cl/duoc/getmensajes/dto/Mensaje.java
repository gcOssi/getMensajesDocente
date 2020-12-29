
package cl.duoc.getmensajes.dto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "cod_docente",
    "sigla",
    "sigla_seccion",
    "hora_inicio",
    "hora_fin",
    "mensaje",
    "tipo"
})
public class Mensaje {

    @JsonProperty("cod_docente")
    private String codDocente;
    @JsonProperty("sigla")
    private String sigla;
    @JsonProperty("sigla_seccion")
    private String siglaSeccion;
    @JsonProperty("hora_inicio")
    private String horaInicio;
    @JsonProperty("hora_fin")
    private String horaFin;
    @JsonProperty("mensaje")
    private String mensaje;
    @JsonProperty("tipo")
    private String tipo;    
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("mensajes")
    private List<Mensaje> mensajeList;
    
    @JsonProperty("cod_docente")
    public String getCodDocente() {
        return codDocente;
    }

    @JsonProperty("cod_docente")
    public void setCodDocente(String codDocente) {
        this.codDocente = codDocente;
    }

    @JsonProperty("sigla")
    public String getSigla() {
        return sigla;
    }

    @JsonProperty("sigla")
    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    @JsonProperty("sigla_seccion")
    public String getSiglaSeccion() {
        return siglaSeccion;
    }

    @JsonProperty("sigla_seccion")
    public void setSiglaSeccion(String siglaSeccion) {
        this.siglaSeccion = siglaSeccion;
    }

    @JsonProperty("hora_inicio")
    public String getHoraInicio() {
        return horaInicio;
    }

    @JsonProperty("hora_inicio")
    public void setHoraInicio(String horaInicio) {
        this.horaInicio = horaInicio;
    }

    @JsonProperty("hora_fin")
    public String getHoraFin() {
        return horaFin;
    }

    @JsonProperty("hora_fin")
    public void setHoraFin(String horaFin) {
        this.horaFin = horaFin;
    }

    @JsonProperty("mensaje")
    public String getMensaje() {
        return mensaje;
    }

    @JsonProperty("mensaje")
    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    @JsonProperty("tipo")
    public String getTipo() {
        return tipo;
    }

    @JsonProperty("tipo")
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    
    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }
	public List<Mensaje> getMensajeList() {
		return mensajeList;
	}

	public void setMensajeList(List<Mensaje> mensajeList) {
		this.mensajeList = mensajeList;
	}  
}
