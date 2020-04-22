/*
 * File: XMLUtils
 * Author: DorSey Q F TANG
 * Created: 2020/4/22
 * CopyRight: All rights reserved
 */
package com.iku.sports.mini.admin.utils;

import com.iku.sports.mini.admin.exception.ApiServiceException;
import com.iku.sports.mini.admin.exception.IkuSportsError;
import com.iku.sports.mini.admin.model.PaymentNotification;
import lombok.extern.slf4j.Slf4j;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;

@Slf4j
public class XMLUtils {
    public static <T> T convertXmlStrToObj(final Class<PaymentNotification> clazz, final String xml) throws ApiServiceException {
        T obj = null;
        try {
            JAXBContext context = JAXBContext.newInstance(clazz);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            StringReader reader = new StringReader(xml);

            obj = (T) unmarshaller.unmarshal(reader);
        } catch (JAXBException e) {
            log.error("Failed to parse the xml: {} to class: {}", xml, clazz.getName());
            throw new ApiServiceException(IkuSportsError.XML_PARSE_ERROR);
        }

        return obj;
    }
}
