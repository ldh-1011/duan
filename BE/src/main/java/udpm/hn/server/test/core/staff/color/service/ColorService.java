package udpm.hn.server.test.core.staff.color.service;

import udpm.hn.server.test.core.common.base.ResponseObject;
import udpm.hn.server.test.entity.Color;

public interface ColorService {
    ResponseObject<?> getAllColor();

    ResponseObject<?> addColor(Color color);


    ResponseObject<?> updateColor(String id, Color color);

    ResponseObject<?> deleteColor(String id);
}
