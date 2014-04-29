package com.megion.site.core.admininterface;

import info.magnolia.cms.beans.runtime.MultipartForm;
import info.magnolia.cms.core.Content;
import info.magnolia.cms.core.NodeData;
import info.magnolia.cms.security.AccessDeniedException;
import info.magnolia.cms.util.NodeDataUtil;
import info.magnolia.context.MgnlContext;
import info.magnolia.jcr.util.SessionUtil;
import info.magnolia.module.admininterface.FieldSaveHandler;
import info.magnolia.module.admininterface.SaveHandlerImpl;
import info.magnolia.module.data.DataModule;

import java.util.ArrayList;

import javax.jcr.Node;
import javax.jcr.PathNotFoundException;
import javax.jcr.RepositoryException;
import javax.jcr.Value;

import org.apache.commons.lang.StringUtils;

@SuppressWarnings("deprecation")
public abstract class DataTypeSaveHandler extends SaveHandlerImpl implements FieldSaveHandler {
	
	protected abstract String getNodeTypeName();
   
    public DataTypeSaveHandler() {
    }

    @Override
    protected void processMultiple(Content node, String name, int type, String[] values) throws RepositoryException,
        PathNotFoundException, AccessDeniedException {

        String typeRootPath = DataModule.getInstance().getTypeDefinitionByName(getNodeTypeName()).getRootPath();

        ArrayList<Value> list = new ArrayList<Value>();
        String dataRepository = DataModule.getRepository();
        if (values != null) {
            for (int j = 0; j < values.length; j++) {
                String path = values[j];
                if (StringUtils.isNotEmpty(path)) {
                    if(!path.startsWith(typeRootPath)) {
                        path = typeRootPath + path;
                    }
                    Node dataNode = SessionUtil.getNode(dataRepository, path);
                    if (dataNode!=null) {
                    	// NULL возможен в случае битых данных, в это случае после сохранения битые данные пропадут
                    	String uuid = SessionUtil.getNode(dataRepository, path).getUUID();
                        Value value = getValue(uuid, type);
                        if (value != null) {
                            list.add(value);
                        }
                    }
                    
                    
                }
            }
        }
        if(list.size() == 0){
            if(node.hasNodeData(name)){
                node.deleteNodeData(name);
            }
        }
        else{
            @SuppressWarnings("unused")
			NodeData data = NodeDataUtil.getOrCreateAndSet(node, name, list.toArray(new Value[list.size()]));
        }
        
    }

    @Override
    public void save(Content parentNode, Content configNode, String name,
            MultipartForm form, int type, int valueType, int isRichEditValue,
            int encoding) throws RepositoryException, AccessDeniedException {
        processMultiple(parentNode, name, type, MgnlContext.getParameterValues(name));
    }
}
