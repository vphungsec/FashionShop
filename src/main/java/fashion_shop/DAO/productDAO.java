package fashion_shop.DAO;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import fashion_shop.entity.Product;
import fashion_shop.entity.ProductCategory;
import fashion_shop.entity.SizeAndColor;
import fashion_shop.entity.SizeAndColor.PK;

@Transactional
@Repository
public class productDAO {
	@Autowired
	SessionFactory factory;
	
	//Get List Product includes Size, Color and Quantity 
	//( mix between 2 Entity: Product & SizeAndColor)
	public List<Object[]> getLMixProd() {
		Session session = factory.getCurrentSession();
		String hql = "select P.idProduct, P.ProdCategory, P.name, P.price, P.image, S.pk.color, S.pk.size, S.quantity  " +
				" from Product P, SizeAndColor S "
				+ "where P.idProduct = S.pk.productID";
		Query query = session.createQuery(hql);
		List<Object[]> listProd = query.list();
		return listProd;
	}
	
	public List<Object[]> getProductDetailByID(String id) {
		Session session = factory.getCurrentSession();
		String hql = "select P.idProduct, P.ProdCategory, P.name, P.price, P.image, S.pk.color, S.pk.size, S.quantity " +
				" from Product P, SizeAndColor S " +
				" where P.idProduct = S.pk.productID and P.idProduct = '" + id + "'";
		Query query = session.createQuery(hql);
		List<Object[]> listProd = query.list();
		return listProd;
	}
	
	// Get List Product DOES NOT includes Size, Color and Quantity 
	//( just Entity: Product)
	public List<Product> getLProd() {
		Session session = factory.getCurrentSession();
		String hql = "from Product";
		Query query = session.createQuery(hql);
		List<Product> listProd = query.list();
		return listProd;
	}
	
	
	
	public List<ProductCategory> getLCat() {
		Session session = factory.getCurrentSession();
		String hql = "from ProductCategory";
		Query query = session.createQuery(hql);
		List<ProductCategory> listCat = query.list();
		return listCat;
	}
	
	public ProductCategory getCat( String id) {
		Session session = factory.getCurrentSession();
		ProductCategory Cat = (ProductCategory) session.get(ProductCategory.class, id);
		return Cat;
	}
	
	public Product getProduct(String idProduct) {
		Session session = factory.getCurrentSession();
		Product pd = (Product) session.get(Product.class, idProduct);
		return pd;
	}
	
	
	
	public List<String> getLColor() {
		List<String> list = new ArrayList<String>();
		
		list.add("White");
		list.add("Black");
		list.add("Brown");
		list.add("Red");
		list.add("Green");
		list.add("Yellow");
		
		return list;
	}
	
	public boolean saveProduct( Product prod) {
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		
		try {
			session.save(prod);
			t.commit();
		} catch(Exception e) {
			t.rollback();
			System.out.println("Insert product Failed");
			return false;
		} finally {
			session.close();
		}
		System.out.println("Insert product Success!");
		return true;
	}
	
	public boolean updateProduct( String prodID,
			String cat,
			String name,
			Float price,
			String image) {
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		
		
		Product prod = (Product) session.get(Product.class, prodID);
		
		prod.setProductCategory(getCat(cat));
		prod.setName(name);
		prod.setPrice(price);
		prod.setImage(image);
		
		try {
			session.update(prod);
			t.commit();
		} catch(Exception e) {
			t.rollback();
			System.out.println("Update product Failed");
			return false;
		} finally {
			session.close();
		}
		System.out.println("Update product Success!");
		return true;
	}
	
	public boolean deleteProduct( String prodID ) {
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		
		
		Product prod = (Product) session.get(Product.class, prodID);
		
		String hql = "DELETE SizeAndColor where pk.productID = " + prodID;
		
		try {
			Query query = session.createQuery(hql);
			int line = query.executeUpdate();
			System.out.println("There's " + line + " lines executes!");
			session.delete(prod);
			t.commit();
		} catch(Exception e) {
			t.rollback();
			System.out.println("Delete product Failed");
			System.out.println(e);
			return false;
		} finally {
			session.close();
		}
		System.out.println("Delete product Success!");
		return true;
	}
	
	public List<SizeAndColor> getLCS( String prodID) {
		Session session = factory.getCurrentSession();
		String hql = "from SizeAndColor where pk.productID = " + prodID;
		Query query = session.createQuery(hql);
		List<SizeAndColor> list = query.list();
		
		return list;
	}
	
	public SizeAndColor getCS( String ID, String color, String size) {
		Session session = factory.getCurrentSession();
		
		SizeAndColor.PK pk = new SizeAndColor.PK();
		pk.setProductID(ID);
		pk.setColor(color);
		pk.setSize(size);
		
		SizeAndColor cs = (SizeAndColor) session.get(SizeAndColor.class, pk);
		
		return cs;
	}
	
	public boolean saveCS(SizeAndColor cs) {
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		
		try {
			session.save(cs);
			t.commit();
		} catch(Exception e) {
			t.rollback();
			System.out.println("Insert cs Failed");
			return false;
		} finally {
			session.close();
		}
		System.out.println("Insert cs Success!");
		return true;
	}
	
	public boolean deleteCS( String id, String color, String size) {
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		
		SizeAndColor.PK pk = new SizeAndColor.PK();
		pk.setProductID(id);
		pk.setColor(color);
		pk.setSize(size);
		
		SizeAndColor cs = (SizeAndColor) session.get(SizeAndColor.class, pk);
		
		if(cs ==null) {
			System.out.println("NULL");
		} else {
			System.out.println("NOT NULL");
		}
		
		try {
			session.delete(cs);
			t.commit();
		} catch(Exception e) {
			t.rollback();
			System.out.println("Delete cs Failed");
			System.out.println(e);
			return false;
		} finally {
			session.close();
		}
		System.out.println("Delete cs Success!");
		return true;
	}
	
	public boolean updateCS( String id, String color, String size, Integer quantity) {
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		
		SizeAndColor.PK pk = new SizeAndColor.PK();
		pk.setProductID(id);
		pk.setColor(color);
		pk.setSize(size);
		
		SizeAndColor cs = (SizeAndColor) session.get(SizeAndColor.class, pk);
		cs.setQuantity(quantity);
	 
		
		try {
			session.update(cs);
			t.commit();
		} catch(Exception e) {
			t.rollback();
			System.out.println("Update cs Failed");
			System.out.println(e);
			return false;
		} finally {
			session.close();
		}
		System.out.println("Update cs Success!");
		return true;
	}
}
