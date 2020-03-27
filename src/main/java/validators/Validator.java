package validators;

/**
 * Interfata validator
 * @param <E> - parametru generic de validat
 */
public interface Validator<E> {
    void validate(E entity) throws ValidationException;
}