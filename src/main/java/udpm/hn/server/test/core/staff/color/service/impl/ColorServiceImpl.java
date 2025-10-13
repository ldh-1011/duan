package udpm.hn.server.test.core.staff.color.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import udpm.hn.server.test.core.common.base.ResponseObject;
import udpm.hn.server.test.core.staff.color.service.ColorService;
import udpm.hn.server.test.entity.Color;
import udpm.hn.server.test.repository.ColorRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ColorServiceImpl implements ColorService {
    private final ColorRepository colorRepository;

    @Override
    public ResponseObject<?> getAllColor(){
        return new ResponseObject<>(
                colorRepository.findAllColors(),
                HttpStatus.OK,
                "Lay thanh cong color"
        );
    }

    @Override
    public ResponseObject<?> addColor(Color color){
        Optional<Color> optionalColor = colorRepository.findByColorName(color.getColorName());
        if(optionalColor.isPresent()){
            return  new ResponseObject<>(null , HttpStatus.CONFLICT ,"color nay da ton tai");
        }
        color.setColorName(color.getColorName());
        colorRepository.save(color);

        return new ResponseObject<>(null , HttpStatus.CREATED ,"color added");
    }

    @Override
    public ResponseObject<?> updateColor(String id, Color color){
        Optional<Color> optionalColor = colorRepository.findById(id);
        if(optionalColor.isEmpty()){
            return  new ResponseObject<>(null , HttpStatus.NOT_FOUND ,"khong co color nay");
        }

        Color oldColor = optionalColor.get();
        oldColor.setColorName(color.getColorName());

        colorRepository.save(oldColor);

        return new ResponseObject<>(color , HttpStatus.OK ,"them thanh cong color");
    }

    @Override
    public ResponseObject<?> deleteColor(String id){
        Optional<Color> optionalColor = colorRepository.findById(id);
        if(optionalColor.isEmpty()){
            return  new ResponseObject<>(null , HttpStatus.NOT_FOUND ,"khong co color nay");
        }
        colorRepository.deleteById(id);

        return new ResponseObject<>(null , HttpStatus.OK ,"delete color thanh cong");
    }
}
