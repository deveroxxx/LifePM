package bakos.life_pm.mapper;

public interface BaseMapper<E, D> {
    D toDto(E entity);
}