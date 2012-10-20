package br.edu.buscg.pojos;

/**
 * Plain Old Java Object com construtor default e métodos getters e setters
 * @author tgmarinho
 */

public class Onibus {
    
	private Integer id;
    private String nome;
    private Double latitude;
    private Double longitude;

    public Onibus() {}
    
    public Integer getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
    
    @Override
    public String toString()
    {
        return "Sou o onibus " + this.getNome() + " e estou em lat = " + this.getLatitude()
                + " e long = " + this.getLongitude();
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Onibus other = (Onibus) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
    
    
    
}
