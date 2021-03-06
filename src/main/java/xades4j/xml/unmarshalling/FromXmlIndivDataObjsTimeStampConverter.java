/*
 * XAdES4j - A Java library for generation and verification of XAdES signatures.
 * Copyright (C) 2010 Luis Goncalves.
 *
 * XAdES4j is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 3 of the License, or any later version.
 *
 * XAdES4j is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License along
 * with XAdES4j. If not, see <http://www.gnu.org/licenses/>.
 */
package xades4j.xml.unmarshalling;

import java.util.List;
import xades4j.properties.IndividualDataObjsTimeStampProperty;
import xades4j.properties.data.BaseXAdESTimeStampData;
import xades4j.properties.data.IndividualDataObjsTimeStampData;
import xades4j.xml.bind.xades.XmlIncludeType;
import xades4j.xml.bind.xades.XmlSignedDataObjectPropertiesType;
import xades4j.xml.bind.xades.XmlXAdESTimeStampType;

/**
 *
 * @author Luís
 */
class FromXmlIndivDataObjsTimeStampConverter
        extends FromXmlBaseTimeStampConverter
        implements SignedDataObjPropFromXmlConv
{
    @Override
    public void convertFromObjectTree(
            XmlSignedDataObjectPropertiesType xmlProps,
            QualifyingPropertiesDataCollector propertyDataCollector) throws PropertyUnmarshalException
    {
        super.convertTimeStamps(
                xmlProps.getIndividualDataObjectsTimeStamp(),
                propertyDataCollector,
                IndividualDataObjsTimeStampProperty.PROP_NAME);
    }

    @Override
    protected BaseXAdESTimeStampData createTSData(String canonAlgUri)
    {
        return new IndividualDataObjsTimeStampData(canonAlgUri);
    }

    @Override
    protected void doSpecificConvert(
            XmlXAdESTimeStampType xmlTS,
            BaseXAdESTimeStampData tsData) throws PropertyUnmarshalException
    {
        IndividualDataObjsTimeStampData indivDOTSData = (IndividualDataObjsTimeStampData)tsData;

        List<XmlIncludeType> includes = xmlTS.getInclude();
        for (XmlIncludeType xmlInc : includes)
        {
            indivDOTSData.addInclude(xmlInc.getURI());
        }
    }

    @Override
    protected void setTSData(
            BaseXAdESTimeStampData tsData,
            QualifyingPropertiesDataCollector propertyDataCollector)
    {
        propertyDataCollector.addIndividualDataObjsTimeStamp((IndividualDataObjsTimeStampData)tsData);
    }
}
