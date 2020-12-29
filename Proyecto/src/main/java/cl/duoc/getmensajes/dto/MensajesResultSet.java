
package cl.duoc.getmensajes.dto;

import java.util.Date;
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
    "mensajes"
})
public class MensajesResultSet {

    @JsonProperty("mensajes")
    private List<Mensaje> mensajes = null;
    @JsonProperty("primer_dia_semana")
    private Date primerDiaSemana;
    @JsonProperty("ultimo_dia_semana")
    private Date ultimoDiaSemana;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("mensajes")
    public List<Mensaje> getMensajes() {
        return mensajes;
    }

    @JsonProperty("mensajes")
    public void setMensajes(List<Mensaje> mensajes) {
        this.mensajes = mensajes;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
