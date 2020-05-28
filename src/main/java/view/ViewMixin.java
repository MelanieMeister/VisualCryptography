/**
 * View-Part of MVC.
 * <p>
 *
 * @author Melanie Meister
 * @version 0.1
 * @since 0.1
 */
package view;

/**
 * Interface which prepare each Interface Class.
 */
public interface ViewMixin {

    /**
     * Methods which can be use with
     * this interface.
     */
    default void init() {
        initializeSelf();
        initializeParts();
        layoutParts();
        setupEventHandlers();
        setupValueChangedListeners();
        setupBindings();
    }

    /**
     * initialize the view and add
     * stylesheets.
     */
    default void initializeSelf() {
    }

    /**
     * part where each viewParts have to be
     * initialize. Each Class with this Interface
     * have to be this method.
     */
    void initializeParts();

    /**
     * part where the layout of each viewParts
     * is to define. Each Class with this Interface
     * have to be this method.
     */
    void layoutParts();

    /**
     * part with all events.
     */
    default void setupEventHandlers() {
    }

    /**
     * part which listen to changed values.
     */
    default void setupValueChangedListeners() {
    }

    /**
     * part of all Bindings for the interface.
     */
    default void setupBindings() {
    }
}