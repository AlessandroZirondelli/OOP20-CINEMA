package utilitiesImpl.FactoryImpl;

import java.util.Optional;

import utilities.Film;

public final class FilmBasicImpl implements Film {
    protected final String type = getClass().getName();
    private final String name; //unique name
    private final String genre;
    private final String description;
    private final Optional<String> coverImagePath;
    private final int duration; //minutes
    private final int id;
    /**
     * @param name
     * @param genre
     * @param description
     * @param coverImagePath
     * @param duration
     * @param id
     */
    FilmBasicImpl(final String name, final String genre, final String description, final Optional<String> coverImagePath, final int duration, final int id) {
        this.name = name;
        this.genre = genre;
        this.description = description;
        this.coverImagePath = coverImagePath;
        this.duration = duration;
        this.id = id;
    }
    /**
     * @return name
     */
    @Override
    public String getName() {
        return this.name;
    }
    /**
     * @return genre
     */
    @Override
    public String getGenre() {
        return this.genre;
    }
    /**
     * @return duration
     */
    @Override
    public int getDuration() {
        return this.duration;
    }
    /**
     * @return converPath
     */
    @Override
    public Optional<String> getCoverPath() {
        return this.coverImagePath;
    }
    /**
     * @return description
     */
    @Override
    public String getDescription() {
        return this.description;
    }
    /**
     * @return id
     */
    public int getID() {
        return this.id;
    }
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        return result;
    }
    /**
     * {@inheritDoc}
     */
   
    @Override
    public String toString() {
        return "FilmBasicImpl [name=" + name + ", genre=" + genre + ", description=" + description + ", coverImagePath="
                + coverImagePath + ", duration=" + duration + ", id=" + id + "]";
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        FilmBasicImpl other = (FilmBasicImpl) obj;
        if (id != other.id)
            return false;
        return true;
    }


}