package com.example.integrationbankservice.model.mapper;

import com.example.integrationbankservice.model.dto.Bank;
import com.example.integrationbankservice.model.entity.BankEntity;
import org.springframework.beans.BeanUtils;

public class BankMapper extends BaseMapper<BankEntity, Bank>{

    @Override
    public BankEntity convertToEntity(Bank dto, Object... args) {
        BankEntity entity = new BankEntity();
        if (dto != null) {
            BeanUtils.copyProperties(dto, entity);
        }
        return entity;
    }

    @Override
    public Bank convertToDto(BankEntity entity, Object... args) {
        Bank dto = new Bank();
        if (entity != null) {
            BeanUtils.copyProperties(entity, dto);
        }
        return dto;
    }
}
