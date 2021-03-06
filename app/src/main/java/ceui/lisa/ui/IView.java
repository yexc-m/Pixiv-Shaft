package ceui.lisa.ui;

import java.util.List;

public interface IView<T> {

    void loadFirst(T data);

    void loadNext(T data);

    void noData();

    void error();

    void clearData();
}
