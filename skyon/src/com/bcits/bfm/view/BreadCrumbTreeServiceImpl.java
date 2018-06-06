package com.bcits.bfm.view;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Repository
public class BreadCrumbTreeServiceImpl implements BreadCrumbTreeService {

	public void addNode(String nodeName, int level, HttpServletRequest request) {
		String referrer = request.getHeader("referer");
		Node node = new Node(nodeName, referrer, level);
		BreadCrumbTree tree = (BreadCrumbTree) request.getSession()
				.getAttribute("breadcrumb");
		if (tree == null) {
			tree = new BreadCrumbTree();
			request.getSession().setAttribute("breadcrumb", tree);
		}
		tree.addNode(node);
	}
}
