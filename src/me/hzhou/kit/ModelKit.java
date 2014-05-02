package me.hzhou.kit;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.ehcache.CacheKit;
import com.jfinal.plugin.ehcache.IDataLoader;

import java.util.List;


/**
 * Created with IntelliJ IDEA.
 * Author: StevenChow
 * Date: 13-5-18
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class ModelKit {
	private Model dao;
    private String cacheNameForOneModel;

    public ModelKit(Model dao, String cacheNameForOneModel){
        this.dao = dao;
        this.cacheNameForOneModel = cacheNameForOneModel;
    }
    public <M extends Model> Page<M> loadModelPage(Page<M> page) {
        List<M> modelList = page.getList();
        for(int i = 0; i < modelList.size(); i++){
            M model = (M)loadModel(modelList.get(i).getInt("id"));
            modelList.set(i, model);
        }
        return page;
    }
    public <M> M loadModel(int id) {
        final int ID = id;
        final Model DAO = dao;
        return (M)CacheKit.get(cacheNameForOneModel, ID, new IDataLoader() {
            @Override
            public Object load() {
                return DAO.findById(ID);
            }
        });
    }
}
